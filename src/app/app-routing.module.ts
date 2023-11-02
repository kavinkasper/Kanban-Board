import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CanActivateService } from 'src/Services/can-activate.service';
import { KanbanBoardComponent } from 'src/kanban-board/kanban-board.component';
import { KanbanColumnComponent } from 'src/kanban-column/kanban-column.component';
import { KanbanLandingviewComponent } from 'src/kanban-landingview/kanban-landingview.component';
import { KanbanLoginComponent } from 'src/kanban-login/kanban-login.component';
import { KanbanRegistrationComponent } from 'src/kanban-registration/kanban-registration.component';
import { PageNotFoundComponent } from 'src/page-not-found/page-not-found.component';
import { ProfileComponent } from 'src/profile/profile.component';

const routes: Routes = [
  {path:"",redirectTo:"/landing", pathMatch:"full" },
  {path:"landing",component:KanbanLandingviewComponent},
  {path:"register",component:KanbanRegistrationComponent,canActivate:[CanActivateService]},
  {path:"login",component:KanbanLoginComponent},
  {path:"boards",component:KanbanBoardComponent,canActivate:[CanActivateService]},
  {path:"boards/:id",component:KanbanColumnComponent,canActivate:[CanActivateService]},
  {path:"profile",component:ProfileComponent,canActivate:[CanActivateService]},
  {path:"**",component:PageNotFoundComponent}

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
