package com.msgc.utils.csv;

import com.msgc.entity.User;
import com.msgc.utils.RandomHeadImageUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CsvUtilAdapter {

    public static List<User> read(String fileName) throws IOException {CsvUtil.read(fileName);
        List<User> userList = new ArrayList<>();
        List<List<String>> cvsResult = CsvUtil.read(fileName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int lineNum =  1;
        List<String> lineSnapshot = new ArrayList<>();
        try {
            for (List<String> line : cvsResult){
                lineSnapshot = line;
                User user = new User();
                user.setAccount(line.get(0));
                user.setPassword(line.get(1).toLowerCase());
                user.setSex(line.get(2));
                user.setNickname(line.get(3));
                user.setRealname(line.get(4));
                user.setHeadImage(RandomHeadImageUtil.next());
                user.setBirthday(sdf.parse(line.get(5)));
                user.setIdcard(line.get(6));
                user.setTel(line.get(7));
                if(line.size() <= 8)
                    continue;
                user.setEmail(line.get(8));
                if(line.size() <= 9)
                    continue;
                user.setQq(line.get(9));
                if(line.size() <= 10)
                    continue;
                user.setHome(line.get(10));
                userList.add(user);
                lineNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(lineNum + " 行解析出错 lineSnapshot:" + lineSnapshot.toString());
        }
        return userList;
    }

}
