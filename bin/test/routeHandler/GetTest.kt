package routeHandler

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GetTest{

    @Test
    fun shouldBeAbleToGetResponse() {
        val get = Get()
        val expectedContentLength = "Content-Length: 1696"
        val request = "GET / HTTP/1.1\\n\" +\n" +
                "        \"Host: localhost:3001\\n\" +\n" +
                "        \"Connection: keep-alive\\n\" +\n" +
                "        \"Cache-Control: max-age=0\\n\" +\n" +
                "        \"sec-ch-ua: \\\" Not A;Brand\\\";v=\\\"99\\\", \\\"Chromium\\\";v=\\\"99\\\", \\\"Google Chrome\\\";v=\\\"99\\\"\\n\" +\n" +
                "        \"sec-ch-ua-mobile: ?0\\n\" +\n" +
                "        \"sec-ch-ua-platform: \\\"macOS\\\"\\n\" +\n" +
                "        \"Upgrade-Insecure-Requests: 1\\n\" +\n" +
                "        \"User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.84 Safari/537.36\\n\" +\n" +
                "        \"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\\n\" +\n" +
                "        \"Sec-Fetch-Site: none\\n\" +\n" +
                "        \"Sec-Fetch-Mode: navigate\\n\" +\n" +
                "        \"Sec-Fetch-User: ?1\\n\" +\n" +
                "        \"Sec-Fetch-Dest: document\\n\" +\n" +
                "        \"Accept-Encoding: gzip, deflate, br\\n\" +\n" +
                "        \"Accept-Language: en-GB,en-US;q=0.9,en;q=0.8\\n\" +\n" +
                "        \"Cookie: Idea-b3c56f7e=12016d35-83c9-49b5-803a-37d4606d94ca\\n"
        val actualContentLength = get.handleGetRequest(request).split("\n")[2].replace("\n", "").replace("\r", "")
        assertEquals(expectedContentLength , actualContentLength)
    }
}