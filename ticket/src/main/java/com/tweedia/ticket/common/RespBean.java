//package com.tweedia.ticket.common;
//
//import lombok.Builder;
//import lombok.Data;
//
//import java.util.Objects;
//
///**
// * @Description: 返回json
// * @Author: Tweedia
// * @Date: 2019/9/17
// */
//@Data
//@Builder
//public class RespBean {
//
//    private int status;
//    private String msg;
//    private Object data;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof RespBean)) {
//            return false;
//        }
//        RespBean result = (RespBean) o;
//        return status == result.status
//                   && Objects.equals(msg, result.msg)
//                   && Objects.equals(data, result.data);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(status, msg, data);
//    }
//}
