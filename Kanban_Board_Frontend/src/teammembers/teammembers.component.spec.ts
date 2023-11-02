import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeammembersComponent } from './teammembers.component';

describe('TeammembersComponent', () => {
  let component: TeammembersComponent;
  let fixture: ComponentFixture<TeammembersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeammembersComponent]
    });
    fixture = TestBed.createComponent(TeammembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
