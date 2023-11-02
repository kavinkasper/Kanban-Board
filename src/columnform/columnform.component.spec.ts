import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColumnformComponent } from './columnform.component';

describe('ColumnformComponent', () => {
  let component: ColumnformComponent;
  let fixture: ComponentFixture<ColumnformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ColumnformComponent]
    });
    fixture = TestBed.createComponent(ColumnformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
