import { NgModule } from '@angular/core';
import { ExtraOptions, InMemoryScrollingOptions, RouterConfigOptions, RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './Pages/login/login.component';
import { NewExerciseComponent } from './Pages/new-exercise/new-exercise.component';
import { NewPlanComponent } from './Pages/new-plan/new-plan.component';
import { NewWorkoutComponent } from './Pages/new-workout/new-workout.component';
import { PtHomeComponent } from './Pages/pt-home/pt-home.component';
import { RegisterComponent } from './Pages/register/register.component';
import { ProfileComponent } from './Pages/settings/profile/profile.component';
import { SettingsComponent } from './Pages/settings/settings.component';
import { UserDataComponent } from './Pages/user-data/user-data.component';
import { UserHomeComponent } from './Pages/user-home/user-home.component';

const routerOptions: ExtraOptions = {
  useHash: false,
  anchorScrolling: 'enabled',
};

const routes: Routes = [
  {
    path: '', pathMatch: 'full', redirectTo:'login'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'register',
    component: RegisterComponent
  },
  {
    path: 'user-home',
    component: UserHomeComponent
  },
  {
    path: 'pt-home',
    component: PtHomeComponent
  },
  {
    path: 'new-exercise',
    component: NewExerciseComponent
  },
  {
    path: 'new-workout',
    component: NewWorkoutComponent
  },
  {
    path: 'new-plan',
    component: NewPlanComponent
  },
  {
    path: 'settings',
    component: SettingsComponent,
    children: [
      {
        path:'profile',
        component: ProfileComponent
      },
      {
        path: 'user-data',
        component: UserDataComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, routerOptions)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
