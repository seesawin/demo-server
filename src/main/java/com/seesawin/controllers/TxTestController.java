package com.seesawin.controllers;

import com.hazelcast.core.Hazelcast;
import com.seesawin.services.TestService;
import com.seesawin.services.TxTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/txTest")
public class TxTestController {
    @Autowired
    TxTestService txTestService;

    @GetMapping("/methodNoTx")
    public String allAccess() {
        txTestService.methodNoTx();
        return "Public methodNoTx.";
    }

    @GetMapping("/methodHasTx")
    public String methodHasTx() {
        txTestService.methodHasTx();
        return "Public methodHasTx.";
    }

    @GetMapping("/methodNoTxThenCallMethodHasTx")
    public String methodNoTxThenCallMethodHasTx() {
        txTestService.methodNoTxThenCallMethodHasTx();
        return "Public methodNoTxThenCallMethodHasTx.";
    }

    @GetMapping("/methodHasTxThenCallMethodNoTx")
    public String methodHasTxThenCallMethodNoTx() {
        txTestService.methodHasTxThenCallMethodNoTx();
        return "Public methodHasTxThenCallMethodNoTx.";
    }

    @GetMapping("/methodHasTxThenCallMethodHasTx")
    public String methodHasTxThenCallMethodHasTx() {
        txTestService.methodHasTxThenCallMethodHasTx();
        return "Public methodHasTxThenCallMethodHasTx.";
    }

    @GetMapping("/methodNoTxThanCallSenondMethodNoTx")
    public String methodNoTxThanCallSenondMethodNoTx() {
        txTestService.methodNoTxThanCallSenondMethodNoTx();
        return "Public methodNoTxThanCallSenondMethodNoTx.";
    }

    @GetMapping("/methodNoTxThanCallSenondMethodHasTx")
    public String methodNoTxThanCallSenondMethodHasTx() {
        txTestService.methodNoTxThanCallSenondMethodHasTx();
        return "Public methodNoTxThanCallSenondMethodHasTx.";
    }

    @GetMapping("/methodHasTxThanCallSenondMethodNoTx")
    public String methodHasTxThanCallSenondMethodNoTx() {
        txTestService.methodHasTxThanCallSenondMethodNoTx();
        return "Public methodHasTxThanCallSenondMethodNoTx.";
    }

    @GetMapping("/methodHasTxThanCallSenondMethodHasTx")
    public String methodHasTxThanCallSenondMethodHasTx() {
        txTestService.methodHasTxThanCallSenondMethodHasTx();
        return "Public methodHasTxThanCallSenondMethodHasTx.";
    }

}
