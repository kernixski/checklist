# Checklists

For the reason why even you should use them, read [this extract explanation of why pilots use (and doctors should)](http://thehealthcareblog.com/blog/2007/12/29/pilots-use-checklists-doctors-dont-why-not-by-maggie-mahar/).

Simple GUI application that allows you to handle yaml-described checklists. A simple webapplication exposes the content and statistics to let you detect weak spots in your processes.

It has support for both tags and milestones :

 * tags will organise your templates and checklists. You use them to retrieve them and to group the statistics
 * milestones define a status in the process the checklist supports. You reach a milestone if all checks before that milestone are ok. Via the milestone, you can see which instances of the checklists reached a given state.

## Releases

Stable versions are release on both a branch (/release/<version>) and with a tag (<version>).

Here you can find the [Release notes for the different versions](https://github.com/kullervo16/checklist/releases).

### Release target

The easiest way to use the project is by launching it in a container. You can find all released versions on <a href="https://hub.docker.com/r/kullervo16/checklist/" target="_blank">dockerhub</a>.

## Templates

The templates are defined in  YAML. This is the syntax :

```yaml
# description of the procedure that the checklist will support
description: Checklist to monitor a first deployment of an application
# tags help you organise your checklists. The tags in the template are automatically added to all checklists that are 
# based on this template. You can add as many as you need
tags:
    - deployment
    - software
# The steps represent the various parts of the procedure.
steps :    
      # the id must be unique within the template
    - id: createDeploymentEnvironment
      # who should perform this action
      responsible: deployer
      # the action to be performed. You should either have an action or a subchecklist (see further)      
      action: request the deployment environment
      # the verification to be executed when the action is performed. Can be a single item or a list (see next step for example)
      check: log on the deployment station
      # an optional milestone that is considered to be reached when this step is ok
      milestone: readyForDeployment
    - id: createApplication
      responsible: deployer
      action: create the application in the proper zone      
      # an example with multiple checks for 1 action (when you want more than 1 action, simply create new steps)
      check:
          - step: open webconsole in the proper zone
          - step: application should be present and green
      # the weight of this step in the progress bar to get a more realistic progress if you want (if not specified : default = 1)
      weight: 5
    - id: verifyFunctioning
      responsible: development
      # the documentation tag will be rendered as a link in the webgui.. You can use it when you need to give context, add a screenshot... 
      # you simply describe it in a document/wiki/webpage/... and point the user into that direction in your action
      documentation: https://github.com/kullervo16/checklist
      # this will launch a child template. This step will be marked ok when the subchecklist reaches 100%.      
      subchecklist: /development/verifyDeployment
      milestone: deployed
      
```

### Simple flow control
There is also the option to create a simple IF function in your checklists.. This allows you to apply some simple flow control and prevents you from creating
multiple templates that do practically the same thing (which will become a burden to keep them in synch when you want to enhance them). This is an example

```yaml
description: Checklist to verify a deployment
tags:
    - tag1
    - tag2  
# this flag is false per default. If you set it to true, the checklist button will not be shown in the templates page...
subchecklistOnly: false  
steps :    
    - id: step1
      responsible: resp1
      options:
          - option1
          - option2
      milestone: milestone1
    - id: step2
      responsible: resp1
      condition:
          - selectionPoint: step1
          - option: option1
      action: action1
      check: check1
      milestone: milestone2
    - id: step3
      responsible: resp1
      condition:
          - selectionPoint: step1
          - option: option2
      action: action1
      check: check1
      milestone: milestone3
    - id: step4
      responsible: resp1
      condition:
          - selectionPoint: step1
          - option: option2
      action: action1
      check: check1
      milestone: milestone4
    - id: step5
      responsible: resp1
      action: action1
      check: check1
      milestone: milestone5      
```
In the GUI this is represented like this : the steps that are not reachable anymore based on your choices are marked in grey.

![alt text](screenshot_choice.png "checklist with options")

### Questions
Sometimes you're not executing actions, but you want to ask a question to make sure the user is reflecting a certain issue. To allow this, you can replace
the action step by a question. The answer to the question will be stored in the checklist. This answer can be a free-form string, or a selection from
some values defined in the template. Here is an example

```yaml
displayName: startProject
# description of the procedure that the checklist will support
description: Checklist to kickoff a new project
# tags help you organise your checklists. The tags in the template are automatically added to all checklists that are 
# based on this template. You can add as many as you need
tags:
    - development
    - software
# The steps represent the various parts of the procedure.
steps :    
      # the id must be unique within the template
    - id: basicDocumentation
      # who should perform this action
      responsible: architect
      # the question to be asked   
      question: is the documentation up-to-date? Please add the link in the answer.
      # you can add checks to see what you mean with "up-to-date"
      check:
          - step: application in the table for the domain
          - step: development team added in the table
          - step: link to the documentation
      # free format answer
      answerType: text      
    - id: testTools
      responsible: architect
      question: which testtools do you use?     
      # this specifies that zero, 1 or more of the options should be selected
      answerType: multiple
      options:
          - unitils
          - selenium
          - soapUI      
    - id: dependencyInjection
      responsible: development
      question: which dependency injection framework do you use?
      # this specifies that only 1 of the options should be selected
      answerType: onlyOne
      options:
          - spring
          - CDI          
     
```
## Web GUI

This simple GUI allows you to select a template and instantiate it in a checklist. The checklist is simply a copy of the YAML template
you select where the GUI allows you to update the status. This enables people without development skills that would be distracted
by the YAML syntax to fill out the checklist.

It applies also some governance : 
 * you cannot change steps marked as done
 * you need to confirm every check point
 * a comment is required for each failing step in order to gather the weaknesses in your process so you can adapt your checklists accordingly

The basic template we saw above is shown like this :

![alt text](screenshot_example.png "checklist in progress")

The Web frontend also serves 2 other purposes :
 * allow you to monitor the progress of a given checklist (f.e. on a television screen in your operations room)
 * provide statistics on usage
 
 The statistics will help you see which templates are widely used, but also where the most errors occur. This may point to
 a spot in your process where there is either something unclear, or your previous steps lack proper checks to make them less
 error prone.

This is an example of a checklist with questions :

![alt text](screenshot_questions.png "a checklist with questions")

### Overview
The system shows you the most recent active checklists for quick access (if you do not know the UUID). Color coding shows which ones are complete (green) and 
which ones are still in progress (blue).

![alt text](screenshot_overview.png "the list of checklists")

There is also a simple stats implemented that shows you which steps of your template fail and what kind of errors you get per step (more will come later on)
![alt text](screenshot_stats.png "the simple template stats")

You can find your checklists based on the tags and reached milestones. They are presented in the tagcloud in the select page
![alt text](screenshot_tagcloud.png "the tagcloud page")

