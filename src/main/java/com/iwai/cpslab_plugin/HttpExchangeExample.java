package com.iwai.cpslab_plugin;

public class HttpExchangeExample  {
    public static String json = "test json";

//    @Override
//    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//
//        if (target.equals("/")) {
//            // リクエストの内容を読み取る
//            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            StringBuilder jsonPayload = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                jsonPayload.append(line);
//            }
//            Client2 c2 = new Client2();
//            c2.runSample2();
//            // JSONの処理
////            System.out.println("受け取ったJSON2: " + jsonPayload.toString());
//            json = jsonPayload.toString();
////            System.out.println(CpsLab.message);
////            CpsLab.message = jsonPayload.toString();
////            System.out.println(CpsLab.message);
//            // レスポンスの設定
//            response.setContentType("application/json");
//            response.setStatus(HttpServletResponse.SC_OK);
//            baseRequest.setHandled(true);
//        }
//    }
    public static void main(String[] args) throws Exception {
//        Server server = new Server(8765);
//        server.setHandler(new HttpExchangeExample());
//        server.start();
//        server.join();
    }

    public static String getJson() {
        return json;
    }
}

