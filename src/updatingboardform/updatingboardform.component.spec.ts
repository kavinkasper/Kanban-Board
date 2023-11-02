import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatingboardformComponent } from './updatingboardform.component';

describe('UpdatingboardformComponent', () => {
  let component: UpdatingboardformComponent;
  let fixture: ComponentFixture<UpdatingboardformComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdatingboardformComponent]
    });
    fixture = TestBed.createComponent(UpdatingboardformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
