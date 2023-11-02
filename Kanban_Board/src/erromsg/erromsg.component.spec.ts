import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErromsgComponent } from './erromsg.component';

describe('ErromsgComponent', () => {
  let component: ErromsgComponent;
  let fixture: ComponentFixture<ErromsgComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ErromsgComponent]
    });
    fixture = TestBed.createComponent(ErromsgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
