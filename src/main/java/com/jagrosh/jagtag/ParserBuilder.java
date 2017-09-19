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
package com.jagrosh.jagtag;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * A Builder for a Parser for easier method-adding
 * 
 * @author John Grosh (jagrosh)
 */
public class ParserBuilder {
    
    private final List<Method> methods;
    private long iterations = 200;
    private int maxLength = 4000;
    private int maxOutput = 1995;
    
    /**
     * Construct the default builder with no methods
     */
    public ParserBuilder()
    {
        methods = new LinkedList<>();
    }
    
    /**
     * Add a single Method
     * @param method - the method to add
     * @return the builder after the method has been added
     */
    public ParserBuilder addMethod(Method method)
    {
        this.methods.add(method);
        return this;
    }
    
    /**
     * Add a Collection of Methods
     * @param methods - the collection to add
     * @return the builder after the methods have been added
     */
    public ParserBuilder addMethods(Collection<Method> methods)
    {
        this.methods.addAll(methods);
        return this;
    }
    
    /**
     * Sets the maximum iterations for the parser
     * @param iterations - the max iterations
     * @return the builder after the max iterations have been set
     */
    public ParserBuilder setMaxIterations(long iterations)
    {
        this.iterations = iterations;
        return this;
    }
    
    /**
     * Sets the maximum internal length of the parser
     * @param length - the max internal length
     * @return the builder after this max has been set
     */
    public ParserBuilder setMaxLength(int length)
    {
        this.maxLength = length;
        return this;
    }
    
    /**
     * Sets the maximum output length for the parser
     * @param length - the max output length
     * @return the builder after the max has been set
     */
    public ParserBuilder setMaxOutput(int length)
    {
        this.maxOutput = length;
        return this;
    }
    
    /**
     * Builds a new Parser based on the current builder
     * @return a new Parser
     */
    public Parser build()
    {
        return new Parser(methods, iterations, maxLength, maxOutput);
    }
}
