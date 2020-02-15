package com.seesawin.services;

import com.seesawin.repository.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
//@Transactional(rollbackFor = Exception.class)
public class TxTestService {
    @Autowired
    UsersMapper usersMapper;

    @Autowired
    TxSecondTestService txSecondTestService;

    public void methodNoTx() {
        log.info("methodNoTx start.");
        usersMapper.selectAll();
        log.info("methodNoTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTx() {
        log.info("methodHasTx start.");
        usersMapper.selectAll();
        log.info("methodHasTx end.");
    }

    public void methodNoTxThenCallMethodHasTx() {
        log.info("methodNoTxThenCallMethodHasTx start.");
        usersMapper.selectAll();
        methodHasTx();
        log.info("methodNoTxThenCallMethodHasTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThenCallMethodNoTx() {
        log.info("methodHasTxThenCallMethodNoTx start.");
        usersMapper.selectAll();
        methodNoTx();
        log.info("methodHasTxThenCallMethodNoTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThenCallMethodHasTx() {
        log.info("methodHasTxThenCallMethodHasTx start.");
        usersMapper.selectAll();
        methodHasTx();
        log.info("methodHasTxThenCallMethodHasTx end.");
    }

    /**********************************************************************************************************/

    public void methodNoTxThanCallSenondMethodNoTx() {
        log.info("methodNoTxThanCallSenondMethodNoTx start.");
        usersMapper.selectAll();
        txSecondTestService.methodNoTx();
        log.info("methodNoTxThanCallSenondMethodNoTx end.");
    }

    public void methodNoTxThanCallSenondMethodHasTx() {
        log.info("methodNoTxThanCallSenondMethodHasTx start.");
        usersMapper.selectAll();
        txSecondTestService.methodHasTx();
        log.info("methodNoTxThanCallSenondMethodHasTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThanCallSenondMethodNoTx() {
        log.info("methodHasTxThanCallSenondMethodNoTx start.");
        usersMapper.selectAll();
        txSecondTestService.methodNoTx();
        log.info("methodHasTxThanCallSenondMethodNoTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThanCallSenondMethodHasTx() {
        log.info("methodHasTxThanCallSenondMethodHasTx start.");
        usersMapper.selectAll();
        txSecondTestService.methodHasTx();
        log.info("methodHasTxThanCallSenondMethodHasTx end.");
    }
}
