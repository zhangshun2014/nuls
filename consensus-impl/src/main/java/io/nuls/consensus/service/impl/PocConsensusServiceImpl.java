package io.nuls.consensus.service.impl;

import io.nuls.account.entity.Account;
import io.nuls.account.entity.Address;
import io.nuls.account.service.intf.AccountService;
import io.nuls.consensus.entity.Agent;
import io.nuls.consensus.entity.ConsensusMember;
import io.nuls.consensus.entity.ConsensusStatusInfo;
import io.nuls.consensus.params.JoinConsensusParam;
import io.nuls.consensus.service.intf.ConsensusService;
import io.nuls.core.constant.ErrorCode;
import io.nuls.core.context.NulsContext;
import io.nuls.core.exception.NulsRuntimeException;

import java.util.List;
import java.util.Map;

/**
 * @author Niels
 * @date 2017/11/9
 */
public class PocConsensusServiceImpl implements ConsensusService {

    private static final ConsensusService INSTANCE = new PocConsensusServiceImpl();
    private AccountService accountService = NulsContext.getInstance().getService(AccountService.class);

    private PocConsensusServiceImpl() {
    }

    public static ConsensusService getInstance() {
        return INSTANCE;
    }

    private void registerAgent(Agent agent, String password) {
        //todo e
    }

    private void joinTheConsensus(Account account, String password, double amount, String agent) {
        //todo
        //check password

        //check block height
        //check consensus state
        //check credit
        //check balance

//        Transaction tx = createTransaction(address,agentMiner,inputs,sign);
//        JoinConsensusEvent event = createJoinEvent( tx);
        //broadcast
        //change consensus status in cache
    }


    @Override
    public void exitTheConsensus(Address address, String password) {
        //验证密码

        //组装申请

        //广播
    }

    @Override
    public List<ConsensusMember> getConsensusMemberList(Map<String, Object> params) {
        return null;
    }

    @Override
    public ConsensusStatusInfo getConsensusInfo(String address) {
        return null;
    }

    @Override
    public void joinTheConsensus(String address, String password, Map<String, Object> paramsMap) {
        Account account = this.accountService.getAccount(address);
        if (null == account) {
            throw new NulsRuntimeException(ErrorCode.FAILED, "The account is not exist,address:" + address);
        }
        if (paramsMap == null || paramsMap.size() < 2) {
            throw new NulsRuntimeException(ErrorCode.NULL_PARAMETER);
        }
        JoinConsensusParam params = new JoinConsensusParam(paramsMap);
        if (params.getCommissionRate() != null) {
            Agent agent = new Agent();
            agent.setAddress(address);
            agent.setDelegateAddress(params.getAgentAddress());
            agent.setDeposit(params.getDeposit());
            agent.setCommissionRate(params.getCommissionRate());
            agent.setIntroduction(params.getIntroduction());
            this.registerAgent(agent, password);
            return;
        }
        this.joinTheConsensus(account, password, params.getDeposit(), params.getAgentAddress());
    }

}