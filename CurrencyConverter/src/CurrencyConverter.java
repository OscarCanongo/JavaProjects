import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import org.json.*;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {

        HashMap<Integer, String> currencyCodes = new HashMap<>();
        String fromCode;
        String toCode;
        double amount;

        // Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "MXN");

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the currency converter.");

        System.out.println("Currency converter from: ");
        System.out.println(
                "1) USD (US DOLLAR) \t" +
                "2) CAD (CANADIAN DOLLAR)\t" +
                "3) EUR (EURO)\t" +
                "4) MXN (MEXICAN PESO)\t"
        );
        fromCode = currencyCodes.get(sc.nextInt());

        System.out.println("Currency converter to: ");
        System.out.println(
                "1) USD (US DOLLAR) \t" +
                "2) CAD (CANADIAN DOLLAR)\t" +
                "3) EUR (EURO)\t" +
                "4) MXN (MEXICAN PESO)\t"
        );
        toCode = currencyCodes.get(sc.nextInt());

        System.out.println("Amount you wish to convert:");
        amount = sc.nextFloat();

        sendHttpGETRequest(fromCode, toCode, amount);

        System.out.println("Thank you for using the currency converter!");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {

        DecimalFormat f = new DecimalFormat("00.00");

        String route = "https://api.currencyapi.com/v3/latest?apikey=cur_live_ttCP09Gn5FIBfNDhl9KlNi8trihLYhx9ZU1kPG2c&base_currency="+fromCode+"&currencies="+toCode;

        URL url = new URL(route);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();

        request.setRequestMethod("GET");
        int responseCode = request.getResponseCode();

        // success
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            inputLine = in.readLine();

            while (inputLine != null) {
                response.append(inputLine);
                inputLine = in.readLine();
            }

            in.close();

            JSONObject obj = new JSONObject(response.toString());
            double exchangeRate = obj.getJSONObject("data").getJSONObject(toCode).getDouble("value");
            System.out.println(f.format(amount) + fromCode + " = " + f.format(exchangeRate * amount) + toCode);
        } else {
            System.out.println("GET Request failed.");
        }
    }
}
