package sample.online.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PythonLib {

	private static Logger logger = LoggerFactory.getLogger(PythonLib.class);

	public static void main(String[] args) throws URISyntaxException {
		Set<String> libSet = new PythonLib().retrieveMostUsedLib();
		Set<String> verSet = new PythonLib().retrieveStableVersion(libSet);
		new PythonLib().writeDependencies(verSet);
	}

    public Set<String> retrieveMostUsedLib() throws URISyntaxException {
        // URI 생성
        String baseUrl = "https://libraries.io/api/search?";
        String apiKey = "76cdb5dd0bfa65ee3da45d7857e63455";
        String platforms = "Pypi";
        String languages = "Python";
        String sort = "dependent_repos_count";

        Set<String> libSet = new LinkedHashSet<>();
        for (int i = 1; i < 5; i++) {
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
                	libSet.add(jsonObject.get("name").asText());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return libSet;
    }

	public Set<String> retrieveStableVersion(Set<String> libSet) throws URISyntaxException {
		String baseUrl = "https://api.pepy.tech/api/v2/projects/";

		Set<String> verSet = new LinkedHashSet<>();

		for (String lib: libSet) {
			try {
				URI uri = new URI(baseUrl + lib);
				logger.debug("URI: {}",uri);

				// API 호출
				URL url = new URL(uri.toString());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("X-API-Key", "AdISfsg5xEBXjWt53zCUNvXwTJlT2slJ");

				// 응답 처리
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuilder response = new StringBuilder();

				while ((inputLine = in.readLine()) != null)
					response.append(inputLine);
				in.close();

				// JSON 응답 파싱
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(response.toString());
				logger.debug("response.toString(): {}", response.toString());
				JsonNode downloadsNode = rootNode.path("downloads");

				// 어제 날짜 구하기
				String yesterday = LocalDate.now().minusDays(1).toString();
				JsonNode yesterdayDownloads = downloadsNode.path(yesterday);

				if (yesterdayDownloads.isEmpty()) {
					logger.debug("어제의 다운로드 데이터가 없습니다.");
					continue;
				}

				// 가장 높은 다운로드 수 찾기
				String highestVersion = null;
				int highestCount = 0;

				Iterator<Map.Entry<String, JsonNode>> fields = yesterdayDownloads.fields();
				while (fields.hasNext()) {
					Map.Entry<String, JsonNode> entry = fields.next();
					String version = entry.getKey();
					int count = entry.getValue().asInt();

					if (count > highestCount) {
						highestCount = count;
						highestVersion = version;
					}
				}

				// 결과 출력
				if (highestVersion != null) {
					logger.debug("가장 많이 다운로드된 버전: {}",highestVersion);
					logger.debug("가장 많이 다운로드된 수: {}", highestCount);
					verSet.add(lib+"=="+highestVersion);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return verSet;
	}

	public void writeDependencies(Set<String> verSet) {
	    // python-dependencies.txt 파일에 verSet 내용 쓰기
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\lib-test\\python-dependencies.txt"))) {
	        for (String dependency : verSet) {
	            writer.write(dependency);
	            writer.newLine(); // 각 의존성마다 줄바꿈
	        }
	        logger.debug("python-dependencies.txt 파일에 작성 완료");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
