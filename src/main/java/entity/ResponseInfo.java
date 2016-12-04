package entity;

/**
 * Created by zhonghaojian on 15/8/11.
 */
public class ResponseInfo {

    public long code;
    public String detail;
    public String detailName;
    public String detailRole;

    //1表示成功
    public ResponseInfo() {
        this.code = 1;
        this.detail = "Success";
    }

    public ResponseInfo(long code, String detail){
        this.code = code;
        this.detail = detail;
    }

    public void setFailWithInfo(String reason){
        this.code = 0;
        this.detail = reason;
    }

    public void setSuccessWithInfo(String successInfo){
        this.code = 1;
        this.detail = successInfo;
    }

    public void setDetailWithInfo(String successInfo, String nameInfo, String roleInfo){
        this.code = 1;
        this.detail = successInfo;
        this.detailName = nameInfo;
        this.detailRole = roleInfo;
    }

    public void addFailInfo(String reason){

        if (this.code == 0)
            this.detail = this.detail + ", " + reason;
        else
            this.detail = reason;
    }

    public void addSuccessInfo(String successInfo){

        if (this.code == 1)
            this.detail = this.detail + ", " + successInfo;
        else
            this.detail = successInfo;
    }
}
