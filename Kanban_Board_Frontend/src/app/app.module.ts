import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { KanbanLandingviewComponent } from 'src/kanban-landingview/kanban-landingview.component';
import { KanbanLoginComponent } from 'src/kanban-login/kanban-login.component';
import { KanbanRegistrationComponent } from 'src/kanban-registration/kanban-registration.component';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {MatButtonModule} from '@angular/material/button';
import { KanbanBoardComponent } from 'src/kanban-board/kanban-board.component';
import {MatCardModule} from '@angular/material/card';
import { ProfileComponent } from 'src/profile/profile.component';
import {MatIconModule} from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatDialogModule} from '@angular/material/dialog';
import { BoardformComponent } from 'src/boardform/boardform.component';
import { TeammembersComponent } from 'src/teammembers/teammembers.component';
import { TaskformComponent } from 'src/taskform/taskform.component';
import {MatRadioModule} from '@angular/material/radio';
import { ColumnformComponent } from 'src/columnform/columnform.component';
import {MatSelectModule} from '@angular/material/select';
import { HeaderComponent } from 'src/header/header.component';
import { UpdatingboardformComponent } from 'src/updatingboardform/updatingboardform.component';
import {MatBadgeModule} from '@angular/material/badge';
import {MatMenuModule} from '@angular/material/menu';
import { DragDropModule } from '@angular/cdk/drag-drop';
import {MatTooltipModule} from '@angular/material/tooltip';
import { TaskeditingformComponent } from 'src/taskeditingform/taskeditingform.component';
import { FotterComponent } from 'src/fotter/fotter.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { PageNotFoundComponent } from 'src/page-not-found/page-not-found.component';
import { HeaderMainComponent } from 'src/header-main/header-main.component';
import { KanbanColumnComponent } from 'src/kanban-column/kanban-column.component';
import { CdkDrag } from '@angular/cdk/drag-drop';
import { CdkDropList } from '@angular/cdk/drag-drop';
import { ErromsgComponent } from 'src/erromsg/erromsg.component';


@NgModule({
  declarations: [
    AppComponent,
    KanbanLandingviewComponent,
    KanbanRegistrationComponent,
    KanbanLoginComponent,
    KanbanBoardComponent,
    ProfileComponent,
    BoardformComponent,
    TeammembersComponent,
    TaskformComponent,
    ColumnformComponent,
    HeaderComponent,
    UpdatingboardformComponent,
    TaskeditingformComponent,
    FotterComponent,
    PageNotFoundComponent,
    HeaderMainComponent,
    KanbanColumnComponent,
    ErromsgComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    FormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatDialogModule,
    MatRadioModule,
    MatSelectModule,
    MatBadgeModule,
    MatMenuModule,
    DragDropModule,
    MatTooltipModule,
    MatCheckboxModule,
    CdkDrag,
    CdkDropList    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
