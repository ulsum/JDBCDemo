package com.ulsum.jdbc.hrapp;

import com.ulsum.jdbc.hrapp.command.*;

import java.util.Scanner;

public class HrApp {

    public static void main(String[] args) {
        System.out.println("1 - 查询部门员工");
        System.out.println("2 - 办理员工入职");
        System.out.println("3 - 调整员工薪资");
        System.out.println("4 - 办理员工离职");
        System.out.println("5 - 分页查询员工");
        System.out.print("请选择功能？");
        Scanner in = new Scanner(System.in);
        Integer cmd = in.nextInt();
        Command command = null;
        switch (cmd) {
            case 1:     // 查询部门员工
                command = new PstmtQueryCommand();
                command.execute();
                break;
            case 2:     // 添加部门员工
                command = new InsertCommand();
                command.execute();
                break;
            case 3:     // 调整员工薪资
                command = new UpdateCommand();
                command.execute();
                break;
            case 4:     // 删除员工信息
                command = new DeleteCommand();
                command.execute();
                break;
            case 5:     // 分页查询员工信息
                command = new PaginationCommand();
                command.execute();
                break;
        }
    }

}
