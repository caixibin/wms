package com._520it.wms.realm;

import com._520it.wms.domain.Employee;
import com._520it.wms.domain.Role;
import com._520it.wms.service.IEmployeeService;
import com._520it.wms.service.IPermissionService;
import com._520it.wms.service.IRoleService;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {
    @Setter
    private IEmployeeService employeeService;
    @Setter
    private IRoleService roleService;
    @Setter
    private IPermissionService permissionService;

    @Override
    public String getName() {
        return "MyRealm";
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Employee current = employeeService.queryByName(username);
        if (current == null) {
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(current, current.getPassword(), ByteSource.Util.bytes(username), getName());
        return info;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Employee current = (Employee) principals.getPrimaryPrincipal();
        //模拟数据库
        List<String> roles = null;
        List<String> perms = null;
        //如果是超级管理员,拥有所有的权限
        if (current.isAdmin()) {
            roles = new ArrayList<>();
            perms = new ArrayList<>();
            List<Role> roleList = roleService.listAll();
            for (Role role : roleList) {
                roles.add(role.getSn());
            }
            perms.add("*:*");
        } else {
            roles = roleService.queryByEmployeeId(current.getId());
            perms = permissionService.queryByemployeeId(current.getId());
        }
        /*//模拟数据库*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(perms);
        return info;
    }
}
