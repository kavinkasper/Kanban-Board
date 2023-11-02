import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KanbanLandingviewComponent } from './kanban-landingview.component';

describe('KanbanLandingviewComponent', () => {
  let component: KanbanLandingviewComponent;
  let fixture: ComponentFixture<KanbanLandingviewComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [KanbanLandingviewComponent]
    });
    fixture = TestBed.createComponent(KanbanLandingviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
