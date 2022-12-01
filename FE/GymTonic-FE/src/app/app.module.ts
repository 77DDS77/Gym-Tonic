import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { LoginComponent } from './Pages/login/login.component';
import { RegisterComponent } from './Pages/register/register.component';
import { UserHomeComponent } from './Pages/user-home/user-home.component';
import { PtHomeComponent } from './Pages/pt-home/pt-home.component';
import { HeaderComponent } from './Components/header/header.component';
import { NavbarComponent } from './Components/navbar/navbar.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NavfootComponent } from './Components/navfoot/navfoot.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AuthInterceptor } from './Interceptors/auth.interceptor';
import { PlanCardComponent } from './Components/plan-card/plan-card.component';
import { WorkoutCardComponent } from './Components/workout-card/workout-card.component';
import { ExerciseCardComponent } from './Components/exercise-card/exercise-card.component';
import { ExDescriptionComponent } from './Components/ex-description/ex-description.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserHomeComponent,
    PtHomeComponent,
    HeaderComponent,
    NavbarComponent,
    NavfootComponent,
    PlanCardComponent,
    WorkoutCardComponent,
    ExerciseCardComponent,
    ExDescriptionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule
  ],
  providers: [
    {
      provide:HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
