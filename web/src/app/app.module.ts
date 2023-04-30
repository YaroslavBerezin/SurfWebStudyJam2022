import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import {TuiLinkModule, TuiNotificationModule, TuiRootModule, TuiTextfieldControllerModule} from '@taiga-ui/core';
import {TuiButtonModule} from '@taiga-ui/core';
import {TuiSvgModule} from '@taiga-ui/core';
import {TuiAccordionModule, TuiElasticContainerModule, TuiStepperModule} from '@taiga-ui/kit';
import {TuiProgressModule} from '@taiga-ui/kit';
import {TuiIslandModule} from '@taiga-ui/kit';
import {TuiInputModule} from '@taiga-ui/kit';
import {TuiTextAreaModule} from '@taiga-ui/kit';
import {TuiErrorModule} from '@taiga-ui/core';
import {TuiFieldErrorPipeModule} from '@taiga-ui/kit';
import {TuiScrollbarModule} from '@taiga-ui/core';
import {TuiAlertModule} from '@taiga-ui/core';
import {TuiInputFilesModule} from '@taiga-ui/kit';
import {TuiThemeNightModule} from '@taiga-ui/core';
import {TuiModeModule} from '@taiga-ui/core';
import {TuiToggleModule} from '@taiga-ui/kit';
import {TuiCheckboxLabeledModule} from '@taiga-ui/kit';

import {TUI_SANITIZER} from '@taiga-ui/core';
import {NgDompurifySanitizer} from '@tinkoff/ng-dompurify';

import { AppComponent } from './app.component';
import { EnrollComponent } from './component/enroll/enroll.component';
import { EnrollWelcomeComponent } from './component/enroll/enroll-welcome/enroll-welcome.component';
import { EnrollApplymentComponent } from './component/enroll/enroll-applyment/enroll-applyment.component';
import { StepFormComponent } from './component/enroll/enroll-applyment/step-form/step-form.component';
import { StepComponent } from './component/enroll/enroll-applyment/step-form/step/step.component';
import { EnrollSuccessComponent } from './component/enroll/enroll-success/enroll-success.component';
import { TestingComponent } from './component/testing/testing.component';
import { TestingStartingComponent } from './component/testing/testing-starting/testing-starting.component';
import { TestingProcessComponent } from './component/testing/testing-process/testing-process.component';
import { TestingEndedComponent } from './component/testing/testing-ended/testing-ended.component';
import { TuiRadioLabeledModule } from '@taiga-ui/kit';
import {ProjectCardComponent} from "./component/card/project-card.component";

@NgModule({
  declarations: [
    AppComponent,
    EnrollComponent,
    EnrollWelcomeComponent,
    EnrollApplymentComponent,
    StepFormComponent,
    StepComponent,
    EnrollSuccessComponent,
    TestingComponent,
    TestingStartingComponent,
    TestingProcessComponent,
    TestingEndedComponent,
    ProjectCardComponent
  ],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,

        TuiRootModule,
        TuiButtonModule,
        TuiSvgModule,
        TuiStepperModule,
        TuiProgressModule,
        TuiIslandModule,
        TuiInputModule,
        TuiTextAreaModule,
        TuiErrorModule,
        TuiFieldErrorPipeModule,
        TuiScrollbarModule,
        TuiAlertModule,
        TuiInputFilesModule,
        TuiThemeNightModule,
        TuiModeModule,
        TuiToggleModule,
        TuiCheckboxLabeledModule,
        TuiRadioLabeledModule,
        TuiLinkModule,
        TuiAccordionModule,
        TuiElasticContainerModule,
        TuiTextfieldControllerModule,
        TuiNotificationModule,
    ],
  providers: [
    {
      provide: TUI_SANITIZER,
      useClass: NgDompurifySanitizer,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
