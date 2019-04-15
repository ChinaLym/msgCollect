package com.msgc.utils.csv;

import com.msgc.entity.User;
import com.msgc.utils.RandomHeadImageUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CsvUtilAdapter {

    public static List<User> read(String fileName) throws IOException {CsvUtil.read(fileName);
        List<User> userList = new ArrayList<>();
        List<List<String>> cvsResult = CsvUtil.read(fileName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (List<String> line : cvsResult){
            User user = new User();
            user.setAccount(line.get(0));
            user.setPassword(line.get(1));
            user.setSex(line.get(2));
            user.setNickname(line.get(3));
            user.setRealname(line.get(4));
            user.setHeadImage(RandomHeadImageUtil.next());
            try {
                user.setBirthday(sdf.parse(line.get(5)));
            } catch (ParseException e) {
                System.err.println(line.get(4) + "出生日期转化出错");
                user.setBirthday(null);
            }
            user.setIdcard(line.get(6));
            userList.add(user);
        }
        return userList;
    }

}
