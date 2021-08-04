package com.jb.coupons.jobs;

import com.jb.coupons.repositories.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * The type Daily job.
 */
@Component
@EnableAsync
@RequiredArgsConstructor
public class DailyJob {

    private final CouponRepository couponRepository;

    /**
     * Daily job task.
     * Runs at 23:01 every day and removes expired coupons from the database.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Scheduled(cron = "0 01 23 * * ?", zone="Asia/Jerusalem")
    public void dailyJobTask() throws InterruptedException{
        couponRepository.deleteByEndDateBefore(Calendar.getInstance().getTime());
    }
}
