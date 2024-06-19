package com.yse.dev;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.descriptor.web.SecurityRoleRef;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class AnalyzeController {
     @GetMapping("/uploadfile")
    public String uploadfileForm(){
         return "req_form";
     }

    @PostMapping("/uploadfile")
    public String uploadfileProcess(MultipartFile file, String data, Model model){
         try {
             System.out.println(file.getSize());
             URL url = new URL("http://3.35.81.123:5000/analyze_img");
             HttpURLConnection con = (HttpURLConnection) url.openConnection();
             String boundary = UUID.randomUUID().toString();
             con.setRequestMethod("POST");
             con.setDoOutput(true);
             con.setRequestProperty("content-Type", "multipart/form-data;boundary=" + boundary);

             OutputStream out = con.getOutputStream();
             DataOutputStream request = new DataOutputStream(out);
             request.writeBytes("--" + boundary + "\r\n");
             request.writeBytes("Content-Disposition: form-data; name=\"data\"\r\n\r\n");
             request.writeBytes(data + "\r\n");

             request.writeBytes("--" + boundary + "\r\n");
             request.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"");
             request.writeBytes(file.getOriginalFilename() + "\"\r\n\r\n");
             request.write(file.getBytes());
             request.writeBytes("\r\n");

             request.writeBytes("--" + boundary + "--\r\n");
             request.flush();
             int respCode = con.getResponseCode();

             //요청결과 코드에 따른 처리
             switch(respCode)
             {
                 case 200:
                     System.out.println("OK");
                     break;
                 case 301:
                 case 302:
                 case 307:
                     System.out.println("Redirect");
                     break;
                 default:
             }

             //요청 후 응답 결과 받기 위한 코드 작성
             InputStream in = con.getInputStream();
             InputStreamReader reader = new InputStreamReader(in, "UTF-8");
             BufferedReader response = new BufferedReader(reader);

             String str = null;
             StringBuffer buff = new StringBuffer();
             while((str = response.readLine()) !=null){
                 buff.append(str + "\n");
             }

             String result = buff.toString().trim();

             //결과 문자열을 Map 객체로 변환
             ObjectMapper mapper = new ObjectMapper();
             Map<String, String> map = mapper.readValue(result, Map.class);
             System.out.println(map.keySet());
             Set<String> keys = map.keySet();
             Iterator<String>itor =  keys.iterator();
             while(itor.hasNext()){
                  String k = itor.next();
                  String v = map.get(k);
                  System.out.println(k + ":" + v);
             }

             model.addAttribute("reqResult", map);

         }catch(Exception e){
            e.printStackTrace();
         }
        return "result";
    }
}
