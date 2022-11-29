package com.DavideDalSanto.GTUser.Exceptions;

import com.DavideDalSanto.GTUser.Entities.RoleType;

public class NonExistingRoleException extends Exception {
    public NonExistingRoleException(RoleType type) {
        super("Role " + type.toString() + " doesn't exist.");
    }
}
