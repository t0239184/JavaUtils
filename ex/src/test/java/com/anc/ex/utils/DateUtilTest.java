package com.anc.ex.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.*;
import java.util.Date;


@Slf4j
public class DateUtilTest {


    @Test
    public void test(){
        Date date = new Date();
        Instant instant = Instant.now();
        log.info("new Date()                       :{}", date);
        log.info("new Date().toInstant()           :{}", date.toInstant());
        log.info("new Date().toInstant.toEpocMilli :{}", date.toInstant().toEpochMilli());
        log.info("new Date().getTime()             :{}", date.getTime());
        log.info("------------------------------------------------------");

        log.info("Instant.now()                    :{}", instant);
        log.info("Instant.now().getEpochSecond()   :{}", instant.getEpochSecond());
        log.info("Instant.now().toEpochMilli()     :{}", instant.toEpochMilli());
        log.info("Instant.now().getNano()          :{}", instant.getNano());
        log.info("------------------------------------------------------");


        for (String zoneId: ZoneId.getAvailableZoneIds()) {
            log.info(zoneId);
        }

        log.info("------------------------------------------------------");
        log.info("ZoneId.systemDefault():{}",ZoneId.systemDefault().toString());
        ZonedDateTime zdtTaipei = instant.atZone(ZoneId.of("Asia/Taipei"));
        ZonedDateTime zdtNewYork = instant.atZone(ZoneId.of("America/New_York"));
        log.info("UTC        :{}",instant);
        log.info("Taipei     :{}",zdtTaipei);
        log.info("NewYork    :{}",zdtNewYork);
        log.info("------------------------------------------------------");


        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("LocalDate.now()                  :{}", localDate);
        log.info("LocalTime.now()                  :{}", localTime);
        log.info("LocalDateTime.now()              :{}", localDateTime);
        log.info("LocalDate.now().atTime(LocalTime.now()):{}",localDate.atTime(localTime));
        log.info("LocalDate.now().atStartOfDay(ZoneId.SystemDefault()):{}",localDate.atStartOfDay(ZoneId.systemDefault()));


        log.info("LocalDate.now()                    :{}",LocalDate.now().toEpochDay());

        log.info("Instant.now().toEpochMilli()                  :{}", Instant.now().toEpochMilli());
        log.info("------------------------------------------------------");

        log.info("OffSetDateTime.now()                          :{}",OffsetDateTime.now());
        log.info("OffSetDateTime.now().toInstant                :{}",OffsetDateTime.now().toInstant());
        log.info("OffSetDateTime.now().toInstant.toEpochMilli() :{}",OffsetDateTime.now().toInstant().toEpochMilli());

        log.info("------------------------------------------------------");

        Instant ins = Instant.parse("2018-08-01T01:13:55.333Z");
        log.info("ins:{}",ins);
        log.info("ins:{}",ins.toEpochMilli());
        Instant ins2 = Instant.parse("2018-08-01T01:13:55Z");
        log.info("ins2:{}",ins2);
        log.info("ins2.:{}",ins2.toEpochMilli());
//        Instant ins3 = Instant.parse("2018-08-01T01:13:55.333");//DateTimeParseException
//        log.info("ins3:{}",ins3);
//        Instant ins4 = Instant.parse("2018-08-01 01:13:55.333");//DateTimeParseException
//        log.info("ins4:{}",ins4);


        //Instant 最好使用Duration
        log.info("ins.plus(Duration.ofMinutes(3):{}",ins.plus(Duration.ofMinutes(3)));
        log.info("ins.plus(Duration.ofDays(30))):{}",ins.plus(Duration.ofDays(30)));
        log.info("ins.plus(Period.ofWeeks(1))):{}",ins.plus(Period.ofWeeks(1)));
        log.info("ins.minus(Period.ofWeeks(1))):{}",ins.minus(Period.ofWeeks(1)));
//        log.info("ins.minus(Period.ofWeeks(1))):{}",ins.plus(Period.ofMonths(2)));//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Months
//        log.info("ins.minus(Period.ofWeeks(1))):{}",ins.plus(Period.ofYears(1)));//java.time.temporal.UnsupportedTemporalTypeException: Unsupported unit: Months






    }

}