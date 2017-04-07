package com.heednow.request.client;

/**
 * Created by System-2 on 4/4/2017.
 */
public class UpdateClientRequest {
        private int id;
        private int noOfOutlets;
        private int noOfDevices;
        private String name;
        private String email;
        private String mobile;
        private String location;
        private String status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNoOfOutlets() {
            return noOfOutlets;
        }

        public void setNoOfOutlets(int noOfOutlets) {
            this.noOfOutlets = noOfOutlets;
        }

        public int getNoOfDevices() {
            return noOfDevices;
        }

        public void setNoOfDevices(int noOfDevices) {
            this.noOfDevices = noOfDevices;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            UpdateClientRequest that = (UpdateClientRequest) o;

            if (id != that.id) return false;
            if (noOfOutlets != that.noOfOutlets) return false;
            if (noOfDevices != that.noOfDevices) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            if (email != null ? !email.equals(that.email) : that.email != null) return false;
            if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
            if (location != null ? !location.equals(that.location) : that.location != null) return false;
            return status != null ? status.equals(that.status) : that.status == null;
        }

        @Override
        public int hashCode() {
            int result = id;
            result = 31 * result + noOfOutlets;
            result = 31 * result + noOfDevices;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (email != null ? email.hashCode() : 0);
            result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
            result = 31 * result + (location != null ? location.hashCode() : 0);
            result = 31 * result + (status != null ? status.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "UpdateClientRequest{" +
                    "id=" + id +
                    ", noOfOutlets=" + noOfOutlets +
                    ", noOfDevices=" + noOfDevices +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", location='" + location + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }

}
