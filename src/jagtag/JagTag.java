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
package jagtag;

import jagtag.libraries.*;

/**
 *
 * @author John Grosh (jagrosh)
 */
public class JagTag {
    
    public static String VERSION = "0.3";
    
    public static String REPOSITORY_URL = "https://github.com/jagrosh/JagTag";
    
    public static ParserBuilder newDefaultBuilder()
    {
        return new ParserBuilder()
            .addMethods(Arguments.getMethods())
            .addMethods(Functional.getMethods())
            .addMethods(Strings.getMethods())
            .addMethods(Variables.getMethods());
    }
}
