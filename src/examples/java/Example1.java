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
package me.jagrosh.jagtag.examples;

import me.jagrosh.jagtag.Parser;
import me.jagrosh.jagtag.ParserBuilder;
import me.jagrosh.jagtag.libraries.Functional;
import me.jagrosh.jagtag.libraries.Strings;
import me.jagrosh.jagtag.libraries.Variables;
import javax.swing.JOptionPane;

/**
 *
 * This is a simple input/output example with a few of the included libraries.
 * Note that the arguments library is not inserted because there is no
 * possibility to include arguments in the single-box input.
 * 
 * @author John Grosh (jagrosh)
 */
public class Example1 {
    
    public static void main(String[] args) {
        Parser parser = new ParserBuilder()
                .addMethods(Functional.getMethods())
                .addMethods(Strings.getMethods())
                .addMethods(Variables.getMethods())
                .build();
        
        String input;
        while(true)
        {
            input = JOptionPane.showInputDialog("Enter JagTag to parse:");
            if(input==null)
                break;
            JOptionPane.showMessageDialog(null, parser.clear().parse(input));
        }
    }
}
