package com.milelu.common.security;

import java.security.Principal;

/**
 * @author MILELU
 * @date 2020/9/15 21:38
 */
public class CustomSocketPrincipal implements Principal {
    private String userName;
    private String id;

    public CustomSocketPrincipal(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getName() {
        return this.getId();
    }

    public String getUserName() {
        return this.userName;
    }

    public String getId() {
        return this.id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CustomSocketPrincipal)) {
            return false;
        } else {
            CustomSocketPrincipal other = (CustomSocketPrincipal)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$userName = this.getUserName();
                Object other$userName = other.getUserName();
                if (this$userName == null) {
                    if (other$userName != null) {
                        return false;
                    }
                } else if (!this$userName.equals(other$userName)) {
                    return false;
                }

                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CustomSocketPrincipal;
    }

    public String toString() {
        return "CustomSocketPrincipal(userName=" + this.getUserName() + ", id=" + this.getId() + ")";
    }
}
