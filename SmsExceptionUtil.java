package tra.irts4.sms.service.pri.util;

import org.apache.commons.lang3.exception.ExceptionUtils;

import bz.brick.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import tra.irts4.sms.service.pub.code.SmsError;

/**
 * 例外處理工具
 * @author Vic
 */
@Slf4j
public class SmsExceptionUtil {
	    
    /**
     * get Exception Msg
     * 
     * @param ex
     */
    public static String getExceptionMsg(Exception ex) {
		String errMsg = null;
    	try {
    		if (ex instanceof ServiceException) { //系統定義的例外
    			ServiceException se = (ServiceException)ex;
        		if(SmsError.class.equals(se.getError().getClass())) {
        			if(se.getError().getCode().equals(SmsError.SMS_ERR_9999999.getCode())) {
        				//NullPointerException
        				if(se.getDetail()!=null) {
        					errMsg = se.getDetail().toString();
        				}
        			}else {
        				errMsg = se.getError().getMessage();
        			}
        		}else {
        			//非SMS定義的例外
        			errMsg = se.getMessage();
        		}
    		}else {
    			errMsg = ex.getMessage();
    		}
    		
    	}catch (Exception e) {
    		log.error(e.getMessage(), e);
		}
    	return errMsg;
    }


    /**
     * handle Exception
	 *
	 * @param ex
     */
    public static void handleException(Exception ex) throws ServiceException {
    	if (ex instanceof ServiceException) {
			ServiceException se = (ServiceException)ex;
    		//if(!(se.getError()!=null && SmsError.class.equals(se.getError().getClass()))) {
    			//非SMS定義的錯(內部使用SERVICE, 由controller print)
    			//log.error(se.getMessage(), se);
    		//}
    		throw se;
		}else {
			log.error(ex.getMessage(), ex);
			String errMsg = ex.getMessage();
			if(ex.getCause() != null) { //簡化訊息
				errMsg = ExceptionUtils.getRootCause(ex).getMessage();
			}
			throw new ServiceException(SmsError.SMS_ERR_9999999, errMsg);
		}
    }
}
