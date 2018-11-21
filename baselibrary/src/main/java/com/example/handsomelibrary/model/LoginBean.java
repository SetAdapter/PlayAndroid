package com.example.handsomelibrary.model;

import java.io.Serializable;

/**
 * Created by Stefan on 2018/4/24.
 */

public class LoginBean implements Serializable{

    /**
     * msg : 操作成功！
     * code : 0
     * rows : {"appUser":{"id":55,"nickname":"bbb","password":null,"photoUrl":null,"photoThum":null,"sex":null,"birth":null,"realname":null,"idcard":null,"email":"294197098@qq.com","address":null,"mobile":null,"status":null,"regSource":null,"level":"L1","gmtCreate":null,"gmtModified":null,"cusNo":"CENV180905161710GXJ"},"expire":"2018-10-05 16:40:06","token":"709993a695504cf9536576d6f21aa8cb"}
     */

    private String msg;
    private int code;
    private RowsBean rows;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public RowsBean getRows() {
        return rows;
    }

    public void setRows(RowsBean rows) {
        this.rows = rows;
    }

    public static class RowsBean implements Serializable{
        /**
         * appUser : {"id":55,"nickname":"bbb","password":null,"photoUrl":null,"photoThum":null,"sex":null,"birth":null,"realname":null,"idcard":null,"email":"294197098@qq.com","address":null,"mobile":null,"status":null,"regSource":null,"level":"L1","gmtCreate":null,"gmtModified":null,"cusNo":"CENV180905161710GXJ"}
         * expire : 2018-10-05 16:40:06
         * token : 709993a695504cf9536576d6f21aa8cb
         */

        private AppUserBean appUser;
        private String expire;
        private String token;

        public AppUserBean getAppUser() {
            return appUser;
        }

        public void setAppUser(AppUserBean appUser) {
            this.appUser = appUser;
        }

        public String getExpire() {
            return expire;
        }

        public void setExpire(String expire) {
            this.expire = expire;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public static class AppUserBean implements Serializable {
            /**
             * id : 55
             * nickname : bbb
             * password : null
             * photoUrl : null
             * photoThum : null
             * sex : null
             * birth : null
             * realname : null
             * idcard : null
             * email : 294197098@qq.com
             * address : null
             * mobile : null
             * status : null
             * regSource : null
             * level : L1
             * gmtCreate : null
             * gmtModified : null
             * cusNo : CENV180905161710GXJ
             */

            private int id;
            private String nickname="";
            private Object password;
            private String photoUrl ="";
            private Object photoThum;
            private Object sex;
            private Object birth;
            private Object realname;
            private Object idcard;
            private String email;
            private Object address;
            private Object mobile;
            private Object status;
            private Object regSource;
            private String level;
            private Object gmtCreate;
            private Object gmtModified;
            private String cusNo;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public Object getPassword() {
                return password;
            }

            public void setPassword(Object password) {
                this.password = password;
            }

            public String getPhotoUrl() {
                return photoUrl;
            }

            public void setPhotoUrl(String photoUrl) {
                this.photoUrl = photoUrl;
            }

            public Object getPhotoThum() {
                return photoThum;
            }

            public void setPhotoThum(Object photoThum) {
                this.photoThum = photoThum;
            }

            public Object getSex() {
                return sex;
            }

            public void setSex(Object sex) {
                this.sex = sex;
            }

            public Object getBirth() {
                return birth;
            }

            public void setBirth(Object birth) {
                this.birth = birth;
            }

            public Object getRealname() {
                return realname;
            }

            public void setRealname(Object realname) {
                this.realname = realname;
            }

            public Object getIdcard() {
                return idcard;
            }

            public void setIdcard(Object idcard) {
                this.idcard = idcard;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public Object getMobile() {
                return mobile;
            }

            public void setMobile(Object mobile) {
                this.mobile = mobile;
            }

            public Object getStatus() {
                return status;
            }

            public void setStatus(Object status) {
                this.status = status;
            }

            public Object getRegSource() {
                return regSource;
            }

            public void setRegSource(Object regSource) {
                this.regSource = regSource;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public Object getGmtCreate() {
                return gmtCreate;
            }

            public void setGmtCreate(Object gmtCreate) {
                this.gmtCreate = gmtCreate;
            }

            public Object getGmtModified() {
                return gmtModified;
            }

            public void setGmtModified(Object gmtModified) {
                this.gmtModified = gmtModified;
            }

            public String getCusNo() {
                return cusNo;
            }

            public void setCusNo(String cusNo) {
                this.cusNo = cusNo;
            }
        }
    }
}
