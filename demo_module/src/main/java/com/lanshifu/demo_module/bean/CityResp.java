package com.lanshifu.demo_module.bean;

import java.util.List;

public class CityResp {

    /**
     * code : 130000
     * name : 河北省
     * pchilds : [{"code":"130100","name":"石家庄市","cchilds":[{"code":"130101","name":"市辖区"},{"code":"130102","name":"长安区"}]},{"code":"130200","name":"唐山市","cchilds":[{"code":"130201","name":"市辖区"},{"code":"130202","name":"路南区"}]}]
     */

    private String code;
    private String name;
    private List<PchildsBean> pchilds;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PchildsBean> getPchilds() {
        return pchilds;
    }

    public void setPchilds(List<PchildsBean> pchilds) {
        this.pchilds = pchilds;
    }

    public static class PchildsBean {
        /**
         * code : 130100
         * name : 石家庄市
         * cchilds : [{"code":"130101","name":"市辖区"},{"code":"130102","name":"长安区"}]
         */

        private String code;
        private String name;
        private List<CchildsBean> cchilds;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<CchildsBean> getCchilds() {
            return cchilds;
        }

        public void setCchilds(List<CchildsBean> cchilds) {
            this.cchilds = cchilds;
        }

        public static class CchildsBean {
            /**
             * code : 130101
             * name : 市辖区
             */

            private String code;
            private String name;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
