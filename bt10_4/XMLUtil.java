package bt10_4;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class XMLUtil {

    public static void exportToXML(List<User> users, String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Users");
            doc.appendChild(root);

            for (User user : users) {
                Element userElement = doc.createElement("User");

                Element username = doc.createElement("Username");
                username.appendChild(doc.createTextNode(user.getUsername()));
                userElement.appendChild(username);

                // Không export password để đảm bảo an toàn

                root.appendChild(userElement);
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<User> importFromXML(String filePath) {
        List<User> users = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) return users;

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);

            NodeList userList = doc.getElementsByTagName("User");
            for (int i = 0; i < userList.getLength(); i++) {
                Element userElement = (Element) userList.item(i);
                String username = userElement.getElementsByTagName("Username").item(0).getTextContent();
                users.add(new User(username, "")); // Chỉ dùng username, bỏ password
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}

