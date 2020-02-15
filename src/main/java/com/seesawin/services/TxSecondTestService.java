package com.seesawin.services;

import com.seesawin.repository.UsersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
//@Transactional(rollbackFor = Exception.class)
public class TxSecondTestService {
    @Autowired
    UsersMapper usersMapper;

    public void methodNoTx() {
        log.info("TxSecondTestService.methodNoTx start.");
        usersMapper.selectAll();
        log.info("TxSecondTestService.methodNoTx end.");
    }

    /**
     * PROPAGATION_REQUIRED
     * 如果当前已经存在事务，那么加入该事务，如果不存在事务，创建一个事务，这是默认的传播属性值。
     * serviceA 和 serviceB 都声明了事务，默认情况下，propagation=PROPAGATION_REQUIRED，整个service调用过程中，只存在一个共享的事务，当有任何异常发生的时候，所有操作回滚。
     * @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
     *
     * 如果当前已经存在事务，那么加入该事务，否则创建一个所谓的空事务（可以认为无事务执行）。
     * serviceA执行时当前没有事务，所以serviceB中抛出的异常不会导致 serviceA回滚。
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void methodHasTx() {
        log.info("TxSecondTestService.methodHasTx start.");
        usersMapper.selectAll();
        log.info("TxSecondTestService.methodHasTx end.");
    }

    public void methodNoTxThenCallMethodHasTx() {
        log.info("TxSecondTestService.methodNoTxThenCallMethodHasTx start.");
        usersMapper.selectAll();
        methodHasTx();
        log.info("TxSecondTestService.methodNoTxThenCallMethodHasTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThenCallMethodNoTx() {
        log.info("TxSecondTestService.methodHasTxThenCallMethodNoTx start.");
        usersMapper.selectAll();
        methodNoTx();
        log.info("TxSecondTestService.methodHasTxThenCallMethodNoTx end.");
    }

    @Transactional(rollbackFor = Exception.class)
    public void methodHasTxThenCallMethodHasTx() {
        log.info("TxSecondTestService.methodHasTxThenCallMethodHasTx start.");
        usersMapper.selectAll();
        methodHasTx();
        log.info("TxSecondTestService.methodHasTxThenCallMethodHasTx end.");
    }

}
