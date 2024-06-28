package com.yse.dev;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yse.dev.entity.ImageFile;
import com.yse.dev.service.ImageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AnalyzeController {
    @Autowired
    ImageService imageService;

     @GetMapping("/uploadfile")
    public String uploadfileForm(){
         return "req_form";
     }

    @PostMapping("/uploadfile")
    public String uploadfileProcess(@RequestParam("file") MultipartFile file,
                                    @RequestParam("data") String data,
                                    @RequestParam("title") String title,
                                    Model model){
         try {
             System.out.println(file.getSize());

//             URL url = new URL("http://3.35.81.123:5000/analyze_img");
             URL url = new URL("http://127.0.0.1:5000/analyze_img");
             HttpURLConnection con = (HttpURLConnection) url.openConnection();
             String boundary = UUID.randomUUID().toString();

             //파일 저장
             file.getOriginalFilename();
             ImageFile imageFile = new ImageFile();
             imageFile.setId(boundary);
             long maxNo = imageService.findMaxSeqNo(); //테이블에서 max sequence가져와서
             System.out.println("maxNo:"+maxNo);
             imageFile.setSeqNo(maxNo + 1);//Max값 +1로 설정

             imageFile.setTruthTitle(title);
             imageFile.setPhoto(file.getBytes());
             System.out.println("원본사이즈:" + file.getSize());
             System.out.println("원본:"+file.getBytes().toString());
             imageService.insert(imageFile);
             ///////////////////////////////

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

             System.out.println(map.keySet().toString());
             Set<String> keys = map.keySet();
             Iterator<String>itor =  keys.iterator();
             while(itor.hasNext()){
                  String k = itor.next();
                 if (k.equals("result_list") ) {
                     Object v = map.get(k);
                     System.out.println(k + ":"+ v.toString());
                     System.out.println(v.toString().contains("genre"));
                     String result1 = v.toString();

                 }
             }

             //불러오기
             String id = boundary;
            String ended64 = Base64.getEncoder().encodeToString(imageService.getImageById(id).getPhoto());


             //model.addAttribute("imgSrc", ended64);
             model.addAttribute("reqResult", map);
             model.addAttribute("imageId", id);

         }catch(Exception e){
            e.printStackTrace();
         }
        return "result";
    }

    @PostMapping("/updateImage")
    public ResponseEntity<String> updateImage(
//        @RequestParam("file") MultipartFile file,
        @RequestParam("imageId") String imageId,
        @RequestParam("predTitle1") String predTitle1,
        @RequestParam("predTitle2") String predTitle2,
        @RequestParam("predTitle3") String predTitle3,
        @RequestParam("trueTitle") String trueTitle
        ) throws IOException {
        //String imageId = UUID.randomUUID().toString();

        //파일 저장
//        file.getOriginalFilename();
        ImageFile imageFile = new ImageFile();
        imageFile.setId(imageId);
//        imageFile.setPhoto(file.getBytes());
        imageFile.setPredTitle1(predTitle1);
        imageFile.setPredTitle2(predTitle2);
        imageFile.setPredTitle3(predTitle3);
        imageFile.setTruthTitle(trueTitle);
        imageService.update(imageFile);

         System.out.println("imageId:"+imageId);
        return new ResponseEntity<String>("OK",  HttpStatus. OK);
    }

    @GetMapping("/viewImage/{id}")
    public String getImageById(@PathVariable("id") String id, Model model ){
         System.out.println("id:"+id);

         ImageFile imageFile = imageService.getImageById(id);
         String encoded64 = Base64.getEncoder().encodeToString(imageFile.getPhoto());
         model.addAttribute("encoded64", encoded64);
         System.out.println("image:"+encoded64+": size:"+imageFile.getPhoto().length);

         return "imgtest";
     }
}
