package org.bonn.se.model.objects.dto;

public class RegistrationResult {
        private boolean result;
        private String reason;

        public boolean getResult() {
            return result;
        }
        public void setResult(boolean res){
            this.result=res;
        }
        public String getReason() {
            return reason;
        }
        public void setReason(String reason){
            this.reason=reason;
       }
}

