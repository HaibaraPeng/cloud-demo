package com.roc.demo.common.feign.sentinel.ext;

import com.alibaba.cloud.sentinel.feign.SentinelContractHolder;
import com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler;
import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.roc.demo.common.core.utils.R;
import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.MethodMetadata;
import feign.Target;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static feign.Util.checkNotNull;

/**
 * @Description 支持自动降级注入 重写 {@link com.alibaba.cloud.sentinel.feign.SentinelInvocationHandler}
 * @Author dongp
 * @Date 2022/6/29 0029 11:25
 */
@Slf4j
public class DemoSentinelInvocationHandler implements InvocationHandler {

    public static final String EQUALS = "equals";

    public static final String HASH_CODE = "hashCode";

    public static final String TO_STRING = "toString";

    private final Target<?> target;

    private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    private FallbackFactory<?> fallbackFactory;

    private Map<Method, Method> fallbackMethodMap;

    DemoSentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch,
                                  FallbackFactory<?> fallbackFactory) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
        this.fallbackFactory = fallbackFactory;
        this.fallbackMethodMap = toFallbackMethod(dispatch);
    }

    DemoSentinelInvocationHandler(Target<?> target, Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
    }

    static Map<Method, Method> toFallbackMethod(Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        Map<Method, Method> result = new LinkedHashMap<>();
        for (Method method : dispatch.keySet()) {
            method.setAccessible(true);
            result.put(method, method);
        }
        return result;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (EQUALS.equals(method.getName())) {
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else if (HASH_CODE.equals(method.getName())) {
            return hashCode();
        } else if (TO_STRING.equals(method.getName())) {
            return toString();
        }

        Object result;
        InvocationHandlerFactory.MethodHandler methodHandler = this.dispatch.get(method);
        // only handle by HardCodedTarget
        if (target instanceof Target.HardCodedTarget) {
            Target.HardCodedTarget<?> hardCodedTarget = (Target.HardCodedTarget<?>) target;
            MethodMetadata methodMetadata = SentinelContractHolder.METADATA_MAP
                    .get(hardCodedTarget.type().getName() + Feign.configKey(hardCodedTarget.type(), method));
            // resource default is HttpMethod:protocol: //url
            if (methodMetadata == null) {
                result = methodHandler.invoke(args);
            } else {
                String resourceName = methodMetadata.template().method().toUpperCase() + ':' + hardCodedTarget.url()
                        + methodMetadata.template().path();
                Entry entry = null;
                try {
                    ContextUtil.enter(resourceName);
                    entry = SphU.entry(resourceName, EntryType.OUT, 1, args);
                    result = methodHandler.invoke(args);
                } catch (Throwable ex) {
                    // fallback handle
                    if (!BlockException.isBlockException(ex)) {
                        Tracer.trace(ex);
                    }
                    if (fallbackFactory != null) {
                        try {
                            return fallbackMethodMap.get(method).invoke(fallbackFactory.create(ex), args);
                        } catch (IllegalAccessException e) {
                            // shouldn't happen as method is public due to being an
                            // interface
                            throw new AssertionError(e);
                        } catch (InvocationTargetException e) {
                            throw new AssertionError(e.getCause());
                        }
                    } else {
                        // 若是R类型 执行自动降级返回R
                        if (R.class == method.getReturnType()) {
                            log.error("feign 服务间调用异常", ex);
                            return R.failed(ex.getLocalizedMessage());
                        } else {
                            throw ex;
                        }
                    }
                } finally {
                    if (entry != null) {
                        entry.exit(1, args);
                    }
                    ContextUtil.exit();
                }
            }
        } else {
            // other target type using default strategy
            result = methodHandler.invoke(args);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SentinelInvocationHandler) {
            DemoSentinelInvocationHandler other = (DemoSentinelInvocationHandler) obj;
            return target.equals(other.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
