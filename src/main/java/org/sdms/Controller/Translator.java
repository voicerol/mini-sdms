package org.sdms.Controller;

import java.io.File;
import java.util.HashMap;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The {@code Translator} class handles the internationalization of messages
 * and language switching functionality. It supports selecting a language (ENG or RU)
 * and retrieving corresponding translated messages from an XML file.
 * <p>
 * The class allows setting the language, loading messages from an XML document
 * (Languages.xml), and retrieving the messages using predefined keys.
 */
public class Translator {

    /**
     * Enum representing the available languages.
     */
    public enum Language {
        ENG, RU
    }

    /**
     * A {@code HashMap} that holds the message keys as keys and the corresponding
     * translated messages as values.
     */
    private static final HashMap<String, String> messages;

    /**
     * A variable that holds the currently selected language.
     */
    private static Language language;

    static {
        messages = new HashMap<String, String>();
        setLanguage(Language.ENG); // Set default language to English
    }

    /**
     * Default constructor for the {@code Translator} class.
     * Currently, this constructor doesn't initialize any variables but is
     * available for potential future extensions.
     */
    public Translator() {
    }

    /**
     * Loads the messages from an XML file (Languages.xml) based on the selected language.
     * <p>
     * This method reads the XML file, parses the keys and values, and then stores them
     * in the {@code messages} HashMap. It associates each key with the appropriate
     * translated message for the selected language.
     * <p>
     * If any error occurs while reading or parsing the XML, an exception is caught
     * and its stack trace is printed.
     */
    public static void getMessagesFromXML() {
        try {
            // Vectors to store keys and values temporarily
            Vector<String> keys = new Vector<String>();
            Vector<String> values = new Vector<String>();

            // Reading the .xml document
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("Languages.xml"));
            document.getDocumentElement().normalize();

            // Get all elements with tag "key" (message identifiers)
            NodeList keysList = document.getElementsByTagName("key");
            // Get all elements with tag "val" (translated messages)
            NodeList valuesList = document.getElementsByTagName("val");

            // Extract keys
            for (int i = 0; i < keysList.getLength(); i++) {
                Node keyNode = keysList.item(i);
                Element keyElement = (Element) keyNode;
                keys.add(keyElement.getAttribute("value"));
            }

            // Extract values corresponding to the selected language
            for (int j = 0; j < valuesList.getLength(); j++) {
                Node valueNode = valuesList.item(j);
                Element valueElement = (Element) valueNode;
                if (valueElement.getAttribute("lang").equals(language.toString())) {
                    values.add(valueElement.getTextContent());
                }
            }

            // Populate the messages HashMap with keys and their corresponding values
            for (int i = 0; i < keys.size(); i++) {
                messages.put(keys.get(i), values.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the translated message for the specified key.
     *
     * @param key The key for which the translated message is required.
     * @return The translated message associated with the provided key, or {@code null}
     *         if the key is not found in the messages.
     */
    public static String getValue(final String key) {
        return messages.get(key);
    }

    /**
     * Returns the currently selected language.
     *
     * @return The currently selected language (either {@code Language.ENG} or {@code Language.RU}).
     */
    public static Language getLanguage() {
        return language;
    }

    /**
     * Sets the selected language for translation.
     *
     * @param language The language to be selected (either {@code Language.ENG} or {@code Language.RU}).
     */
    public static void setLanguage(Language language) {
        Translator.language = language;
    }
}
