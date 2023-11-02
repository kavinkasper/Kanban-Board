import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanRegistrationComponent } from './kanban-registration.component';

describe('KanbanRegistrationComponent', () => {
  let component: KanbanRegistrationComponent;
  let fixture: ComponentFixture<KanbanRegistrationComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KanbanRegistrationComponent]
    });
    fixture = TestBed.createComponent(KanbanRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
