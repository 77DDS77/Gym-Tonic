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
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NavfootComponent } from './Components/navfoot/navfoot.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AuthInterceptor } from './Interceptors/auth.interceptor';
import { PlanCardComponent } from './Components/plan-card/plan-card.component';
import { WorkoutCardComponent } from './Components/workout-card/workout-card.component';
import { ExerciseCardComponent } from './Components/exercise-card/exercise-card.component';
import { ExDescriptionComponent } from './Components/ex-description/ex-description.component';
import { NewExerciseComponent } from './Pages/new-exercise/new-exercise.component';
import { NewWorkoutComponent } from './Pages/new-workout/new-workout.component';
import { NewPlanComponent } from './Pages/new-plan/new-plan.component';
import { SweetAlert2Module } from '@sweetalert2/ngx-sweetalert2';
import { ExerciseFormComponent } from './Components/Forms/exercise-form/exercise-form.component';
import { WorkoutFormComponent } from './Components/Forms/workout-form/workout-form.component';
import { PlanFormComponent } from './Components/Forms/plan-form/plan-form.component';
import { RawPlanFormComponent } from './Components/Forms/raw-plan-form/raw-plan-form.component';
import { RawWorkoutFormComponent } from './Components/Forms/raw-workout-form/raw-workout-form.component';
import { RawExerciseFormComponent } from './Components/Forms/raw-exercise-form/raw-exercise-form.component';
import { UserDataComponent } from './Pages/user-data/user-data.component';
import { DataWorkoutComponent } from './Components/data-workout/data-workout.component';
import { DataExerciseComponent } from './Components/data-exercise/data-exercise.component';
import { UserFoundComponent } from './Components/user-found/user-found.component';
import { DetailsComponent } from './Components/user-found/details/details.component';
import { SubDetailsComponent } from './Components/user-found/details/sub-details/sub-details.component';
import { ManageUserComponent } from './Components/manage-user/manage-user.component';
import { ManageExerciseComponent } from './Components/manage-user/manage-exercise/manage-exercise.component';
import { ManageWorkoutComponent } from './Components/manage-user/manage-workout/manage-workout.component';
import { ManagePlanComponent } from './Components/manage-user/manage-plan/manage-plan.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    UserHomeComponent,
    PtHomeComponent,
    HeaderComponent,
    NavfootComponent,
    PlanCardComponent,
    WorkoutCardComponent,
    ExerciseCardComponent,
    ExDescriptionComponent,
    NewExerciseComponent,
    NewWorkoutComponent,
    NewPlanComponent,
    ExerciseFormComponent,
    WorkoutFormComponent,
    PlanFormComponent,
    RawPlanFormComponent,
    RawWorkoutFormComponent,
    RawExerciseFormComponent,
    UserDataComponent,
    DataWorkoutComponent,
    DataExerciseComponent,
    UserFoundComponent,
    DetailsComponent,
    SubDetailsComponent,
    ManageUserComponent,
    ManageExerciseComponent,
    ManageWorkoutComponent,
    ManagePlanComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    FontAwesomeModule,
    SweetAlert2Module,
    SweetAlert2Module.forRoot()
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
