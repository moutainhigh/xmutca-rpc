package com.xmutca.rpc.core.consumer.cluster;

import com.xmutca.rpc.core.config.RpcClientConfig;
import com.xmutca.rpc.core.rpc.RpcRequest;
import com.xmutca.rpc.core.rpc.RpcResponse;
import com.xmutca.rpc.core.transport.Client;
import com.xmutca.rpc.core.transport.ClientGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 失败安全
 * 快速失败，只发起一次调用，失败立即报错，通常用于非幂等性的写操作。
 * @version Revision: 0.0.1
 * @author: weihuang.peng
 * @Date: 2019-11-08
 */
@Slf4j
public class FailSafeClusterInvoker extends AbstractClusterInvoker {

    @Override
    protected RpcResponse doInvoke(RpcRequest rpcRequest, RpcClientConfig rpcClientConfig, ClientGroup groups) {
        Client client = select(groups);
        try {
            return client.send(rpcRequest);
        } catch (Exception e) {
            log.error("Failsafe ignore exception: " + e.getMessage(), e);
        }
        return new RpcResponse();
    }
}
