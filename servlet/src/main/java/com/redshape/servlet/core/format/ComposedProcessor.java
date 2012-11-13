/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.servlet.core.format;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.utils.Commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/10/12
 * Time: 3:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComposedProcessor implements IRequestFormatProcessor {

    public static final String SKIP_ADDITIONAL_PROCESSING =
            "__requestFormatProcessor_SKIP_ADDITIONAL_PROCESSING";

    private List<IRequestFormatProcessor> processors = new ArrayList<IRequestFormatProcessor>();

    public ComposedProcessor(List<IRequestFormatProcessor> processors) {
        Commons.checkNotNull(processors);
        Commons.checkArgument( !processors.isEmpty() );

        this.processors = processors;
    }

    @Override
    public SupportType check(IHttpRequest request) throws ProcessingException {
        return SupportType.MAY;
    }

    @Override
    public void process(IHttpRequest request) throws ProcessingException {
        List<IRequestFormatProcessor> targetList = new ArrayList<IRequestFormatProcessor>();
        List<IRequestFormatProcessor> mustList = new ArrayList<IRequestFormatProcessor>();
        for ( IRequestFormatProcessor processor : this.processors ) {
            if ( processor == null ) {
                continue;
            }

            SupportType type = processor.check(request);
            switch (type) {
                case MUST:
                    mustList.add(processor);
                break;
                case MAY:
                case SHOULD:
                    targetList.add(processor);
                default:
            }

            if ( request.getAttribute(SKIP_ADDITIONAL_PROCESSING) != null
                    && request.getAttribute(SKIP_ADDITIONAL_PROCESSING)
                              .equals(Boolean.TRUE) ) {
                break;
            }
        }

        if ( targetList.isEmpty() && mustList.isEmpty() ) {
            throw new IllegalArgumentException("No suitable format processor found to " +
                    "handle given request object");
        }

        for ( IRequestFormatProcessor processor : mustList ) {
            processor.process(request);
        }

        /**
         * Only single main processor
         */
        for ( IRequestFormatProcessor processor : targetList ) {
            targetList.get(0).process(request);
        }
    }
}
