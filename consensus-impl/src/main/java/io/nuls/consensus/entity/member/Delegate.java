package io.nuls.consensus.entity.member;

import io.nuls.core.chain.entity.BaseNulsData;
import io.nuls.core.chain.entity.Na;
import io.nuls.core.context.NulsContext;
import io.nuls.core.crypto.VarInt;
import io.nuls.core.exception.NulsRuntimeException;
import io.nuls.core.utils.io.NulsByteBuffer;
import io.nuls.core.utils.io.NulsOutputStreamBuffer;
import io.nuls.core.utils.log.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author Niels
 * @date 2017/12/4
 */
public class Delegate extends BaseNulsData {
    private Na deposit;
    private String delegateAddress;

    public Na getDeposit() {
        return deposit;
    }

    public void setDeposit(Na deposit) {
        this.deposit = deposit;
    }

    public String getDelegateAddress() {
        return delegateAddress;
    }

    public void setDelegateAddress(String delegateAddress) {
        this.delegateAddress = delegateAddress;
    }

    @Override
    public int size() {
        int size = 0;
        try {
            size += VarInt.sizeOf(deposit.getValue());
            size += delegateAddress.getBytes(NulsContext.DEFAULT_ENCODING).length;

        } catch (UnsupportedEncodingException e) {
            Log.error(e);
            throw new NulsRuntimeException(e);
        }
        return size;
    }

    @Override
    public void serializeToStream(NulsOutputStreamBuffer buffer) throws IOException {
        buffer.writeVarInt(deposit.getValue());
        buffer.writeBytesWithLength(delegateAddress);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) {
        this.deposit = Na.valueOf(byteBuffer.readVarInt());
        this.delegateAddress = byteBuffer.readString();
    }
}