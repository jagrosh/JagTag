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
package jagtag.examples;

import jagtag.Method;
import jagtag.ParseException;
import jagtag.Parser;
import jagtag.ParserBuilder;

/**
 *
 * This example shows the ParseException throwing an exception all the way
 * up the stack so that the final parsed text is the message of the exception.
 * 
 * @author John Grosh (jagrosh)
 */
public class Example4 {
    
    public static void main(String[] args) {
        Parser parser = new ParserBuilder()
                .addMethod(new Method("check", 
                        (env,in) -> {
                            if(in[0].equals("throw"))
                                throw new ParseException("No throwing!");
                            return in[0];
                        }))
                .build();
        System.out.println(parser.parse("{check:this and {check:that and {check:something else}}}"));
        System.out.println(parser.parse("{check:this and {check:that and {check:throw}}}"));
    }
}
