package sample.online.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import bxm.common.annotaion.BxmCategory;

/**
 * Hello Controller
 *
 * @author sysadmin
 */
@RestController
@RequestMapping("/")
@BxmCategory(logicalName = "JavaLib", description = "JavaLib", author = "sysadmin")
public class JavaLib {

	private static Logger logger = LoggerFactory.getLogger(JavaLib.class);

	@BxmCategory(logicalName = "main", description = "main", author = "sysadmin")
	public static void main(String[] args) throws URISyntaxException {
		new JavaLib().retrieveMostUsedLib();
	}

    @BxmCategory(logicalName="retrieveMostUsedLib", description="retrieveMostUsedLib", author="sysadmin")
    public void retrieveMostUsedLib() throws URISyntaxException {
        // URI 생성
        String baseUrl = "https://libraries.io/api/search?";
        String apiKey = "76cdb5dd0bfa65ee3da45d7857e63455";
        String platforms = "Maven";
        String languages = "Java";
        String sort = "dependent_repos_count";

        Set<String> rankSet = new LinkedHashSet<>();
        for (int i = 1; i < 2; i++) {
            URI uri = new URI(baseUrl
                    + "api_key=" + apiKey
                    + "&platforms=" + platforms
                    + "&languages=" + languages
                    + "&sort=" + sort
                    + "&page=" + i);
            logger.debug("URI::{}", uri);

            try {
                // API 호출
                URL url = new URL(uri.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // 응답 처리
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) 
                    response.append(inputLine);
                in.close();

                // JSON 응답 파싱
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonArray = objectMapper.readTree(response.toString());

                // name 속성 값 저장
                for (JsonNode jsonObject : jsonArray) 
                    rankSet.add(jsonObject.get("name").asText());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // dependencies.json 파일에 rankSet 내용 쓰기
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode rootNode = objectMapper.createObjectNode();
            ArrayNode dependenciesArray = objectMapper.createArrayNode();
            
            for (String dependency : rankSet) {
                dependenciesArray.add(dependency);
            }
            
            rootNode.set("dependencies", dependenciesArray);

            // 파일에 쓰기
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\lib-test\\maven-dependencies.json"))) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, rootNode);
            }

            logger.debug("maven-dependencies.json 파일에 작성 완료");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}