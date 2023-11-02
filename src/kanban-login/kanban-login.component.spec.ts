import { ComponentFixture, TestBed } from '@angular/core/testing';
import { KanbanLoginComponent } from './kanban-login.component';

describe('KanbanLoginComponent', () => {
  let component: KanbanLoginComponent;
  let fixture: ComponentFixture<KanbanLoginComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KanbanLoginComponent]
    });
    fixture = TestBed.createComponent(KanbanLoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
