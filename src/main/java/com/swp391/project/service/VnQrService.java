package com.swp391.project.service;

import com.swp391.project.service.impl.VnQrServiceImp;
import org.springframework.stereotype.Service;

@Service
public class VnQrService implements VnQrServiceImp{

    @Override
    public String urlQrCode(double amount, String addInfo) {
        return String.format("https://img.vietqr.io/image/BIDV-61510000560383-print.png?amount=%s&addInfo=%s&accountName=CTTNHHMMT", amount, addInfo);
    }
}
