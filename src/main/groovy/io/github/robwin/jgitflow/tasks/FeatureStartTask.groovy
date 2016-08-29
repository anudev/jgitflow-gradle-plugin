/*
 *
 *  Copyright 2015 Robert Winkler
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package io.github.robwin.jgitflow.tasks
import com.atlassian.jgitflow.core.JGitFlow
import com.atlassian.jgitflow.core.command.FeatureStartCommand
import io.github.robwin.jgitflow.tasks.credentialsprovider.CredentialsProviderHelper
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FeatureStartTask extends DefaultTask {

    @TaskAction
    void start(){
        String featureName = project.property('featureName')
        CredentialsProviderHelper.setupCredentialProvider(project)
        JGitFlow flow = JGitFlow.get(project.rootProject.rootDir)
        FeatureStartCommand startCommand = flow.featureStart(featureName)
        if (project.hasProperty('allowUntracked')) {
            Boolean allowUntracked = project.property('allowUntracked')
            startCommand.setAllowUntracked(allowUntracked)
        }
        if (project.hasProperty('pushFeature')) {
            Boolean pushFeature = project.property('pushFeature')
            startCommand.setPush(pushFeature)
        }
        startCommand.call()

        flow.git().close()
    }
}
