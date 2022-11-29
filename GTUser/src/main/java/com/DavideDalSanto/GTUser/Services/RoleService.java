package com.DavideDalSanto.GTUser.Services;

import com.DavideDalSanto.GTUser.Entities.Role;
import com.DavideDalSanto.GTUser.Entities.RoleType;
import com.DavideDalSanto.GTUser.Exceptions.NonExistingRoleException;
import com.DavideDalSanto.GTUser.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository rr;

    public List<Role> getAll() {
        return rr.findAll();
    }

//    public Page<Role> getAllPaginate(Pageable p) {
//        return rr.findAll(p);
//    }
//
//    public Role getById(Long id) throws ByIdNotFoundException {
//        Optional<Role> found = rr.findById(id);
//        if (found.isPresent()) {
//            return found.get();
//        }
//        throw new ByIdNotFoundException("Role", id);
//
//    }

    public void save(Role r) {
        rr.save(r);
    }

    public String delete(Long id) {

        rr.deleteById(id);

        return "Role deleted successfully.";

    }

    public Role findByRoleType(RoleType type) throws NonExistingRoleException {
        Optional<Role> found = rr.findByRoleType(type);
        if(found.isPresent()){
            return found.get();
        }else{
            throw new NonExistingRoleException(type);
        }
    }
}
