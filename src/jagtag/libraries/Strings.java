/*
 * Copyright 2016 John Grosh (jagrosh).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jagtag.libraries;

import java.io.UnsupportedEncodingException;
import jagtag.Method;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * Strings Library
 * Used for String manipulations and encoding
 * 
 * @author John Grosh (jagrosh)
 */
public class Strings {

    public static Collection<Method> getMethods() {
        return Arrays.asList(
            // makes all letters lowercase
            new Method("lower", (e,i) -> i[0].toLowerCase()),
                
            // makes all letters uppercase
            new Method("upper", (e,i) -> i[0].toUpperCase()),
            
            // returns the length of the provided string
            new Method("length", (e,i) -> Integer.toString(i[0].length())),
            
            // encodes the text to UTF-8 (url standard)
            new Method("url", (e,i) -> {
                try {
                    return URLEncoder.encode(i[0], "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    return i[0];
                }
            }),
            
            // replaces some text with other text
            new Method("replace", (e,i) -> i[2].replace(i[0],i[1]), "|with:", "|in:"),
            
            // replaces some text with other text based on a regular expression
            // supports capture groups
            new Method("replaceregex", (e,i) -> {
                try {
                    return i[2].replaceAll(i[0], i[1]);
                } catch(Exception ex) {
                    return i[2];
                }
            }, "|with:", "|in:")
        );
    }
    
}
